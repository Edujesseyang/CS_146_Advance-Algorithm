package Heap.ProgramHW1;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

public class MaxHeap {
    private ArrayList<Student> students;

    public MaxHeap(int capacity) {
        students = new ArrayList<Student>(capacity);
    }

    public MaxHeap(Collection<Student> collection) {
        students = new ArrayList<Student>(collection);
        for (int i = size() / 2 - 1; i >= 0; i--) {
            maxHeapify(i);
        }
    }

    public Student getMax() {
        if (size() < 1) {
            throw new IndexOutOfBoundsException("No maximum value:  the heap is empty.");
        }
        return students.get(0);
    }

    public Student extractMax() {
        Student value = getMax();
        students.set(0, students.get(size() - 1));
        students.remove(size() - 1);
        maxHeapify(0);
        return value;
    }

    public int size() {
        return students.size();
    }

    public void insert(Student elt) {
        if (elt == null) { // check if input is null
            throw new InvalidParameterException("Null parameter");
        }

        students.add(elt); // add to the end
        bubbleUp(students.size() - 1); // bubble up the last new element
    }

    public void addGrade(Student elt, double gradePointsPerUnit, int units) {
        if (elt == null) { // check if input is null
            throw new InvalidParameterException("Null parameter");
        }

        int currentInd = students.indexOf(elt); // get the index of the target
        if (currentInd == -1) { // if student can't be found, throw exception
            throw new NoSuchElementException("No such this student present");
        }

        elt.addGrade(gradePointsPerUnit, units); // update the target

        // check if target needs bubbleUp
        if (!bubbleUp(currentInd)) { // if target has been bubbled up at least once, it doesn't need bubble down
            maxHeapify(currentInd); // otherwise, bubble it down
        }
    }

    private boolean bubbleUp(int index) {
        if (index <= 0) { // if input is the root or negative
            return false;
        }

        boolean isSwaped = false; // a flag to mark is the input element has been swaped with its parent

        int parentInd = parent(index); // find parent
        while (students.get(parentInd).compareTo(students.get(index)) < 0) {
            swap(index, parentInd); // swap current and it's parent
            isSwaped = true; // mark as it has been swapped
            index = parentInd; // update current
            parentInd = parent(index); // find new parent
        }

        return isSwaped;
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private int left(int index) {
        return 2 * index + 1;
    }

    private int right(int index) {
        return 2 * index + 2;
    }

    private void swap(int from, int to) {
        Student val = students.get(from);
        students.set(from, students.get(to));
        students.set(to, val);
    }

    private void maxHeapify(int index) {
        int left = left(index);
        int right = right(index);
        int largest = index;
        if (left < size() && students.get(left).compareTo(students.get(largest)) > 0) {
            largest = left;
        }
        if (right < size() && students.get(right).compareTo(students.get(largest)) > 0) {
            largest = right;
        }
        if (largest != index) {
            swap(index, largest);
            maxHeapify(largest);
        }
    }
}
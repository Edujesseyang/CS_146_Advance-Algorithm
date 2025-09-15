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

        // part2: init all students' index
        for (int i = 0; i < students.size(); i++) {
            students.get(i).setIndex(i);
        }
    }

    public Student getMax() {
        if (size() < 1) {
            throw new IndexOutOfBoundsException("No maximum value:  the heap is empty.");
        }
        return students.get(0);
    }

    public Student extractMax() {
        if (size() < 1) {
            throw new IndexOutOfBoundsException("No maximum value:  the heap is empty.");
        }

        Student value = getMax();
        students.set(0, students.get(size() - 1));

        students.get(0).setIndex(0); // part2:  change the max's index
        value.setIndex(-1); // part2: change the last one's index

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
        elt.setIndex(students.size() - 1);
        bubbleUp(students.size() - 1); // bubble up the last new element
    }

    public void addGrade(Student elt, double gradePointsPerUnit, int units) {
        if (elt == null) { // check if input is null
            throw new InvalidParameterException("Null parameter");
        }

        int currentInd = elt.getIndex(); // get the index of the target (part2 new)
        if (currentInd == -1) { // if student can't be found, throw exception
            throw new NoSuchElementException("No such this student present");
        }

        elt.addGrade(gradePointsPerUnit, units); // update the target

        // check if target needs bubbleUp
        if (!bubbleUp(currentInd)) { // if target has been bubbled up at least once, it doesn't need bubble down
            maxHeapify(currentInd); // otherwise, bubble it down
        }
    }

    // private helper methods:

    private boolean bubbleUp(int index) {
        boolean isSwapped = false; // a flag to mark if the input element has been swapped

        while (index > 0) {
            int parentInd = parent(index); // find parent

            if (students.get(parentInd).compareTo(students.get(index)) >= 0) {
                break; // break if parent is already bigger
            }

            swap(index, parentInd); // swap current and it's parent
            isSwapped = true; // mark as it has been swapped
            index = parentInd; // update current
        }

        return isSwapped;
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
        Student fromObj = students.get(from);
        Student toObj = students.get(to);

        students.set(from, toObj);
        students.set(to, fromObj);

        fromObj.setIndex(to); // part2: swap indexes
        toObj.setIndex(from);

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
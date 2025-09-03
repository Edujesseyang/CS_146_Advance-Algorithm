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
        //Please write me.  I should add the given student into the heap,
        //following the insert algorithm from the videos.

        // Solution:
        if (elt == null) { // check if input is valid
            throw new InvalidParameterException("null input");
        }

        if (students.isEmpty()) { // case: empty list
            students.add(elt);
            return;
        }

        students.add(elt); // add to the end
        int currentInd = students.size() - 1; // init cur index
        int parentInd = parent(currentInd); // find cur's father index

        while (parentInd >= 0) { // if father is present
            if (students.get(currentInd).compareTo(students.get(parentInd)) <= 0) {
                return; // if cur < its father, done, return
            }
            swap(currentInd, parentInd);
            currentInd = parentInd; // if not, update cur to its father's position
            parentInd = parent(currentInd); // find new father index
        }
    }

    // just want to try
    public void insertRecursively(Student elt) {
        if (elt == null) { // check if input is valid
            throw new InvalidParameterException("Null parameter");
        }

        if (students.isEmpty()) { // case: empty list
            students.add(elt);
            return;
        }

        students.add(elt); // add to the end
        bubbleUp(students.size() - 1); // shift the new adding element all the way up
    }

    private void bubbleUp(int index) {
        if (index <= 0) { // base case: input is the root, or negative
            return;
        }

        int parentInd = parent(index); // find parent
        if (students.get(parentInd).compareTo(students.get(index)) >= 0) {
            return; // if parent is bigger, done
        }

        swap(index, parentInd); // swap current and it's parent
        bubbleUp(parentInd); // call recursion, input is the parent
    }

    public void addGrade(Student elt, double gradePointsPerUnit, int units) {
        //Please write me.  I should change the student's gpa (using a method
        //from the student class), and then adjust the heap as needed using
        //the changeKey algorithm from the videos.

        // Solution:
        int currentInd = students.indexOf(elt); // get the index of the target
        if(currentInd == -1){ // if student can't be found, throw exception
            throw new NoSuchElementException("No such this student present");
        }

        elt.addGrade(gradePointsPerUnit, units); // update target

        int parentInd = parent(currentInd);  // find cur's parent

        if (students.get(parentInd).compareTo(students.get(currentInd)) < 0) {
            bubbleUp(currentInd); // check if cur is greater than its parent, then is needs to be bubbleUp
        }
        maxHeapify(currentInd); // otherwise, drain down
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
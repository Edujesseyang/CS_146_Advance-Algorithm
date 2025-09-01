package Heap;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MyMaxHeap {
    private ArrayList<Integer> list;

    public MyMaxHeap() {
        this.list = new ArrayList<>();
    }

    public MyMaxHeap(ArrayList<Integer> input) {
        // new a list
        this.list = new ArrayList<>();
        if (input.isEmpty()) {
            return;
        }

        // add every element to list from input, check if its father is smaller
        for (int i = 0; i < input.size(); i++) {
            list.add(input.get(i));
            int currentInd = i;
            int fatherInd = getParentInd(currentInd);
            // while father is smaller, keeping shifting new add to the root
            while (fatherInd >= 0) { // use childInd for to stop

                if (list.get(fatherInd) >= list.get(currentInd)) {
                    break; // when father > child, break
                }

                swap(fatherInd, currentInd);
                currentInd = fatherInd; // child is new father now
                fatherInd = getParentInd(currentInd);
            }
        }
    }

    public MyMaxHeap(ArrayList<Integer>... inputs) {
        // create list
        this.list = new ArrayList<>();
        for (ArrayList<Integer> input : inputs) {
            list.addAll(input);
        }

        betterHeapify();
    }

    private void heapify() {
        int size = list.size(); // get Size
        for (int i = getParentInd(size - 1); i >= 0; i--) {// loop backward from the father of last element
            int current = i;
            while (true) {
                int leftInd = getLeftChildInd(current); // def left child index
                int rightInd = getRightChildInd(current); // def right child index
                int largerChildInd = current; // assume father is largest
                if (leftInd < size && list.get(leftInd) > list.get(largerChildInd)) {
                    largerChildInd = leftInd; // compare father with left, update largest is left is larger
                }
                if (rightInd < size && list.get(rightInd) > list.get(largerChildInd)) {
                    largerChildInd = rightInd; // compare father with right, update largest is left is larger
                }

                if (largerChildInd == current) { // if father is still the largest, go ahead to next iteration
                    break;
                }

                swap(current, largerChildInd); // swap father and larger child
                current = largerChildInd; // key, now the cur goes to child position, moving down to check child's children
            }
        }
    }

    private void betterHeapify() {
        for (int i = getSize() / 2 - 1; i >= 0; i--) {
            shiftDown(i);
        }
    }

    private int getParentInd(int i) {
        return (i - 1) / 2;
    }

    private int getLeftChildInd(int i) {
        return 2 * i + 1;
    }

    private int getRightChildInd(int i) {
        return 2 * i + 2;
    }

    private void swap(int i, int j) {
        int tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }

    private void shiftUpRecursively(int index) {
        int parentInd = getParentInd(index);
        if (parentInd < 0 || list.get(parentInd) >= list.get(index)) {
            return;
        }
        swap(parentInd, index);
        shiftUpRecursively(parentInd);
    }

    private void shiftDownRecursively(int index) {
        if (index >= getSize() / 2) {
            return;  // base case: input is a leaf
        }
        // find its children
        int leftChildInd = getLeftChildInd(index);
        int rightChildInd = getRightChildInd(index);

        int largest = index; // init the largest one is itself, between 3 of them
        if (leftChildInd < getSize() && list.get(leftChildInd) > list.get(index)) {
            largest = leftChildInd; // update larger if leftChild is greater
        }
        if (rightChildInd < getSize() && list.get(rightChildInd) > list.get(index)) {
            largest = rightChildInd; // update larger if rightChild is greater
        }

        if (largest == index) { // if no update at all, stop recursion
            return;
        }
        swap(index, largest); // swap
        shiftDownRecursively(largest); // recursive call
    }

    private void shiftUp(int index) {
        while (index > 0) { // if index == 0, we can just stop
            int parentInd = getParentInd(index);
            if (list.get(parentInd) > list.get(index)) {
                return; // heap structure holds, stop
            }
            swap(parentInd, index);
            index = parentInd;
        }
    }

    private void shiftDown(int index) {
        while (index < getSize() / 2) { // only for parent, don't care about leaves
            int leftChildInd = getLeftChildInd(index);
            int rightChildInd = getRightChildInd(index);

            int largest = leftChildInd; // assume left is greater

            if (rightChildInd < getSize() && list.get(rightChildInd) > list.get(leftChildInd)) {
                largest = rightChildInd; // compare left vs right, update largest
            }
            if (list.get(index) >= list.get(largest)) { // if i is greater than largest, done
                return;
            }
            swap(largest, index); // swap
            index = largest; // update i

        }
    }

    private void shiftDownBounded(int current, int end) {
        while (current < end / 2) {
            int leftChildInd = getLeftChildInd(current);
            int rightChildInd = getRightChildInd(current);

            if (leftChildInd > end) {
                return;
            }

            int largest = leftChildInd;
            if (rightChildInd > end && list.get(rightChildInd) > list.get(largest)) {
                largest = rightChildInd;
            }

            if (list.get(current) > list.get(rightChildInd)) {
                return;
            }
            swap(current, largest);
            current = largest;
        }
    }

    public int getSize() {
        return list.size();
    }

    public int getHeight() {
        if (list.size() == 0) {
            return 0;
        }
        return (int) (Math.log(list.size()) / Math.log(2)) + 1;
    }

    public int getMaxVal() {
        if (getSize() == 0) {
            throw new NoSuchElementException("Heap is empty");
        }
        return list.get(0);
    }

    public int getMinVal() {
        if (getSize() == 0) {
            throw new NoSuchElementException("Heap is empty");
        }
        int firstLeaf = getSize() / 2;
        int min = Integer.MAX_VALUE;
        for (int i = firstLeaf + 1; i < getSize(); i++) {
            int tmp = list.get(i);
            if (tmp < min) {
                min = tmp;
            }
        }
        return min;
    }

    public void insert(int num) {
        list.add(num); // add to the end first
        int currentInd = list.size() - 1; //
        int parentInd = getParentInd(currentInd);

        while (parentInd >= 0) { // when there is parent
            if (list.get(currentInd) <= list.get(parentInd)) {
                return; // if cur smaller or equal to its father, return
            }
            swap(currentInd, parentInd); // if not, swap them
            currentInd = parentInd; // cur in on its father's position
            parentInd = getParentInd(currentInd); // find new parent's index
        }
    }

    public void betterInsert(int num) {
        list.add(num);
        shiftUp(getSize() - 1);
    }


    public int delete(int index) {
        if (index < 0 || index >= getSize()) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
        if (getSize() == 0) {
            throw new NoSuchElementException("Empty Heap");
        }
        //swap last and deleting
        int deletingVal = list.get(index);

        if (index == getSize() - 1) { // handling edge, if delete the last element, no need to shifting
            list.remove(index);
            return deletingVal;
        }

        swap(index, getSize() - 1); // swap the deleting one and last one
        list.remove(getSize() - 1); // remove deleting element from the list

        // check if we need shiftUp or shiftDown
        int parentInd = getParentInd(index);
        if (parentInd >= 0 && list.get(parentInd) < list.get(index)) {
            shiftUp(index); // if parent exists and smaller, shift up
            return deletingVal;
        }
        shiftDown(index); // default case, shift down
        return deletingVal;
    }

    public void sortInplace() {
        if (getSize() <= 0) {
            return;
        }
        int end = getSize() - 1;
        while (end > 0) {
            swap(0, end);
            shiftDownBounded(0, end--);
        }
    }

    public ArrayList<Integer> copySorted() {
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < getSize(); i++) {
            result.add(0); // pre-fill 0s
        }

        MyMaxHeap tmpCopy = new MyMaxHeap(this.list);
        for (int i = getSize() - 1; i >= 0; i--) {
            result.set(i, tmpCopy.delete(0));
        }

        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int nodesPerLevel = 1;
        int lvl = getHeight();
        while (i < list.size()) {
            for (int j = 0; j <= lvl * lvl; j++) {
                sb.append(" "); // adding space before first element every level
            }

            for (int j = 0; j < nodesPerLevel; j++) { // adding correct amount of elements per lvl
                if (i < getSize()) {
                    sb.append(list.get(i)); // add element
                }

                for (int k = 0; k <= lvl * lvl; k++) {
                    sb.append(" "); // add space between elements
                }
                i++;
            }
            nodesPerLevel *= 2; // update num of elements for next lvl
            sb.append("\n");
            lvl--; // count down lvl
        }
        return sb.toString();
    }

}

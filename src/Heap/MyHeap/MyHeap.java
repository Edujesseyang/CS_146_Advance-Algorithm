package Heap.MyHeap;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MyHeap {
    ArrayList<Integer> heapList;

    public MyHeap() {
        heapList = new ArrayList<>(); // constructing empty list
    }

    @SafeVarargs
    public MyHeap(ArrayList<Integer>... lists) {
        heapList = new ArrayList<>(); // init list

        for (ArrayList<Integer> list : lists) { // add all to list
            heapList.addAll(list);
        }

        heapify(); // heapify
    }

    public int size() {
        return this.heapList.size();
    }

    public int getMax() {
        return this.heapList.get(0);
    }

    public String toString() {
        return this.heapList.toString();
    }

    public void insert(int val) {
        this.heapList.add(val); // put to the end
        shiftUp(size() - 1); // shift up
    }

    public void insert(ArrayList<Integer> list) {
        for (Integer integer : list) {
            insert(integer); // insert all
        }
    }

    public int delete(int i) {
        if (i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException("Ouf of bound");
        }
        if (size() == 0) {
            throw new NoSuchElementException("Empty heap");
        }

        int deletingVal = this.heapList.get(i);
        swap(i, size() - 1);
        this.heapList.remove(size() - 1);

        int parent = getParent(i);
        if (parent >= 0 && this.heapList.get(parent) < this.heapList.get(i)) {
            shiftUp(i);
            return deletingVal;
        }
        shiftDown(i, size() - 1);
        return deletingVal;
    }

    public boolean contains(int val) {
        for (int i = 0; i < size(); i++) {
            if (this.heapList.get(i) == val) {
                return true;
            }
        }
        return false;
    }


    // helper methods:

    private void heapify() {
        for (int i = size() / 2; i >= 0; i--) {
            shiftDown(i, size());
        }
    }

    private void shiftUp(int i) {
        while (i > 0) {
            int parentInd = getParent(i);
            if (parentInd < 0 || heapList.get(parentInd) > heapList.get(i)) {
                return;
            }
            swap(parentInd, i);
            i = parentInd;
        }
    }

    private void shiftDown(int i, int endAt) {
        while (i < endAt) {
            int leftChildInd = getLeftChild(i);
            int rightChildInd = getRightChild(i);
            int largest = i;

            if (leftChildInd < endAt && heapList.get(largest) < heapList.get(leftChildInd)) {
                largest = leftChildInd;
            }
            if (rightChildInd < endAt && heapList.get(largest) < heapList.get(rightChildInd)) {
                largest = rightChildInd;
            }

            if (largest == i) {
                return;
            }

            swap(largest, i);
            i = largest;
        }
    }

    private void swap(int a, int b) {
        int tmp = heapList.get(a);
        heapList.set(a, heapList.get(b));
        heapList.set(b, tmp);
    }

    private int getParent(int i) {
        return (i - 1) / 2;
    }

    private int getLeftChild(int i) {
        return i * 2 + 1;
    }

    private int getRightChild(int i) {
        return i * 2 + 2;
    }
}
